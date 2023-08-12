import com.example.chatgpttalk.JSONClasses.Message
import com.example.chatgpttalk.JSONClasses.RequestGPT
import com.example.chatgpttalk.JSONClasses.ResponseData
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class GPTResponse {
    private var client = OkHttpClient()
    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    fun  getResponse(requestMessage:  String) : String {
        val gptResponse = StringBuilder()
        //gptResponse.append("User: $requestMessage\n")
        // токен из личного кабинета
        val apiKey = "secret-key"
        // адрес api для взаимодействия с чат-ботом
        val endpoint = "https://api.openai.com/v1/chat/completions"
        // набор соообщений диалога с чат-ботом
        val messages = arrayListOf<Message>()

        val builder =  OkHttpClient.Builder()
        builder.connectTimeout(300, TimeUnit.SECONDS)
        builder.readTimeout(300, TimeUnit.SECONDS)
        builder.writeTimeout(300, TimeUnit.SECONDS)
        client = builder.build()

        if (requestMessage.isEmpty()) println("Запрос не должен быть пустым!")

        val message = Message("user", requestMessage)

        messages.add(message)
        val requestData = RequestGPT("gpt-3.5-turbo", messages)
        val gson = Gson()
        val json = gson.toJson(requestData)
        val body: RequestBody = json.toRequestBody(mediaType)

        val httpRequest : Request = Request.Builder()
            .url(endpoint)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()


        val response = client.newCall(httpRequest).execute()
        val jsonResponse = response.body!!.string()
        val rootObject = JsonParser.parseString(jsonResponse).asJsonObject

        val responseData = gson.fromJson(rootObject, ResponseData::class.java)

        val choices = responseData.Choices

        if (choices.isEmpty())
            println("No choices were returned by the API")
        val choice = choices[0]

        val responseMessage = choice.Message

        messages.add(responseMessage)

        gptResponse.append(responseMessage.Content.trim())

        return gptResponse.toString()
    }
}
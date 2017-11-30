# scala-slack

scala-slack is a simple, extensible client library for Scala that provides an interface to the [Slack](http://slack.com) API.

_Note: scala-slack is a partial implementation of the Slack API. The Slack API is under heavy development, and this library
is subject to frequent change._

### Supported Methods

 - api.test
 - auth.test
 - channels.history
 - channels.list
 - channels.setTopic
 - chat.delete
 - chat.postMessage
 - chat.update
 - im.close
 - im.history
 - im.list
 - im.mark
 - im.open

## Download

scala-slack is listed on Maven Central and can be included in your project by adding this line to your build.sbt:
```
libraryDependencies += "com.flyberrycapital" %% "scala-slack" % "0.3.1"
```
scala-slack supports Scala 2.10, 2.11, and 2.12.

## Usage

First, instantiate a SlackClient object.

```scala
import com.flyberrycapital.slack.SlackClient

val s = new SlackClient(<YOUR_API_TOKEN>)
```

You can then use Slack API methods:
```scala
s.chat.postMessage("#yourchannel", "Hello World!")
```

You can also edit and delete messages easily by using the returned PostMessageResponse object:
```scala
val response = s.chat.postMessage("#yourchannel", "Hello World!")

s.chat.update(response, "This is my update.")
s.chat.delete(response)
```

Message history can be retrieved with the channels.history and channels.historyStream methods:
```scala
val response = s.channels.list()
val channel = response.channels(0)

val history = s.channels.history(channel.id)
println(history.messages)

// get stream of messages in descending order
val historyStream = s.channels.historyStream(channel.id)

historyStream foreach println
```

All implemented API methods are documented in the code with Scaladoc.

## Extending the library

scala-slack can easily be extended to accommodate new API methods and functionality.

For example, if Slack adds a chat.poke method, one could write a new version of the Chat class:
```scala
import com.flyberrycapital.slack.HttpClient
import com.flyberrycapital.slack.Methods.Chat

class PokeChat(httpClient: HttpClient, apiToken: String) extends Chat(httpClient, apiToken) {

   def poke(userID: String) = {
      val response = httpClient.post(
        "chat.poke",
        Map("user_id" -> userID, "token" -> apiToken)
      )

      // handle poke JSON response here...
   }

}
```

and then you can incorporate this into a custom version of SlackClient:

```scala
import com.flyberrycapital.slack.SlackClient

class PokeSlackClient(apiToken: String) extends SlackClient(apiToken) {

   override val chat = new PokeChat(httpClient, apiToken)

}
```

That being said, we hope that if you implement additional API methods yourself, you'll consider adding them to this project :)

## License

This project is licensed under the [MIT license](http://opensource.org/licenses/MIT).


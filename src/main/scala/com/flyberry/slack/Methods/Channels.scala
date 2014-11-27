/*
 * Copyright (c) 2014 Flyberry Capital, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.flyberry.slack.Methods

import com.flyberry.slack.{SlackChannel, SlackMessage, HttpClient}
import org.joda.time.DateTime
import play.api.libs.json.{JsObject, JsValue}


/**
 * The container for Slack's 'channels' methods (https://api.slack.com/methods).
 *
 * <i>Note: This is a partial implementation, and some (i.e. most) methods are unimplemented.</i>
 */
class Channels(httpClient: HttpClient, apiToken: String) {

   import com.flyberry.slack.Responses._

   /**
    * https://api.slack.com/methods/channels.history
    *
    * @param channel The channel ID to fetch history for.
    * @param params A map of optional parameters and their values.
    * @return ChannelHistoryResponse
    */
   def history(channel: String, params: Map[String, String] = Map()): ChannelHistoryResponse = {

      val cleanedParams = params + ("channel" -> channel, "token" -> apiToken)

      val responseDict = httpClient.get("channels.history", cleanedParams)

      val messages = (responseDict \ "messages").as[List[JsObject]] map { (x) =>
         val user = Option((x \ "user").asOpt[String]
            .getOrElse((x \ "user").asOpt[String]
            .getOrElse(null)))

         SlackMessage(
            (x \ "type").as[String],
            (x \ "ts").as[String],
            user,
            (x \ "text").asOpt[String],
            (x \ "is_starred").asOpt[Boolean].getOrElse(false),
            (x \ "attachments").asOpt[List[JsValue]].getOrElse(List()),
            new DateTime(((x \ "ts").as[String].toDouble * 1000).toLong)
         )
      }

      ChannelHistoryResponse(
         (responseDict \ "ok").as[Boolean],
         messages,
         (responseDict \ "has_more").asOpt[Boolean].getOrElse(false),
         (responseDict \ "is_limited").asOpt[Boolean].getOrElse(false)
      )
   }

   /**
    * A wrapper around the channels.history method that allows users to stream through a channel's past messages
    * seamlessly without having to worry about pagination and multiple queries.
    *
    * @param channel The channel ID to fetch history for.
    * @param params A map of optional parameters and their values.
    * @return Iterator of SlackMessages, ordered by time in descending order.
    */
   def historyStream(channel: String, params: Map[String, String] = Map()): Iterator[SlackMessage] = {
      new Iterator[SlackMessage] {
         var hist = history(channel, params = params)
         var messages = hist.messages

         def hasNext = messages.nonEmpty

         def next() = {
            val m = messages.head
            messages = messages.tail

            if (messages.isEmpty && hist.hasMore) {
               hist = history(channel, params = params + ("latest" -> m.ts))
               messages = hist.messages
            }

            m
         }
      }
   }

   /**
    * https://api.slack.com/methods/channels.list
    *
    * @param params A map of optional parameters and their values.
    * @return A ChannelListResponse object.
    */
   def list(params: Map[String, String] = Map()): ChannelListResponse = {

      val cleanedParams = params + ("token" -> apiToken)

      val responseDict = httpClient.get("channels.list", cleanedParams)

      val channels = (responseDict \ "channels").as[List[JsObject]] map { (x) =>

         SlackChannel(
            (x \ "id").as[String],
            (x \ "name").as[String],
            (x \ "created").as[Int],
            (x \ "creator").as[String],
            (x \ "is_archived").as[Boolean],
            (x \ "is_member").as[Boolean],
            (x \ "members").as[List[String]],
            (x \ "num_members").as[Int],
            (x \ "topic").as[JsValue],
            (x \ "purpose").as[JsValue],
            new DateTime((x \ "created").as[Int])
         )

      }

      ChannelListResponse((responseDict \ "ok").as[Boolean], channels)
   }
}

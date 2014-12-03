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

package com.flyberrycapital.slack

import org.joda.time.DateTime
import play.api.libs.json.JsValue

/**
 * Class for representing a Slack channel
 *
 * @param id The channel ID.
 * @param name The channel name.
 * @param created A UNIX timestamp corresponding to the channel creation date/time.
 * @param creator The user ID of the user who created the channel.
 * @param isArchived Denotes whether the channel is archived.
 * @param isMember Denotes whether the querying user is a member of this channel.
 * @param members A list of user IDs of the users in this channel.
 * @param numMembers The number of members in this channel.
 * @param topic A Play JsValue object containing topic information. Refer to Slack API documentation for possible fields.
 * @param purpose A Play JsValue object containing purpose information. Refer to Slack API documentation for possible fields.
 * @param createdDate The 'created' field as a Joda DateTime object.
 */
case class SlackChannel(id: String, name: String, created: Int, creator: String, isArchived: Boolean, isMember: Boolean,
                        members: List[String], numMembers: Int, topic: JsValue, purpose: JsValue, createdDate: DateTime)

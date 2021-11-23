package com.jobo.commonmvvm.net.interception.logging.util

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.regex.Pattern
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/11/10 1:08 下午
 *
 */
class CharacterHandler private constructor() {
    companion object {
        //emoji过滤器
        val EMOJI_FILTER: InputFilter = object : InputFilter {
            var emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
            )

            override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,dend: Int): CharSequence? {
                val emojiMatcher = emoji.matcher(source)
                return if (emojiMatcher.find()) {
                    ""
                } else null
            }
        }
        /**
         * json 格式化
         *
         * @param json
         * @return
         */
        @JvmStatic
        fun jsonFormat(json: String): String {
            var jsonVar = json
            if (TextUtils.isEmpty(jsonVar)) {
                return "Empty/Null json content"
            }
            var message: String
            try {
                jsonVar = jsonVar.trim { it <= ' ' }
                message = if (jsonVar.startsWith("{")) {
                    val jsonObject = JSONObject(jsonVar)
                    jsonObject.toString(4)
                } else if (jsonVar.startsWith("[")) {
                    val jsonArray = JSONArray(jsonVar)
                    jsonArray.toString(4)
                } else {
                    jsonVar
                }
            } catch (e: JSONException) {
                message = jsonVar
            } catch (error: OutOfMemoryError) {
                message = "Output omitted because of Object size"
            }
            return message
        }

        /**
         * xml 格式化
         *
         * @param xml
         * @return
         */
        @JvmStatic
        fun xmlFormat(xml: String?): String? {
            if (TextUtils.isEmpty(xml)) {
                return "Empty/Null xml content"
            }
            val message: String?
            message = try {
                val xmlInput: Source =
                    StreamSource(StringReader(xml))
                val xmlOutput =
                    StreamResult(StringWriter())
                val transformer =
                    TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
            } catch (e: TransformerException) {
                xml
            }
            return message
        }
    }

    init {
        throw IllegalStateException("you can't instantiate me!")
    }
}
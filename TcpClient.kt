import java.io.*
import java.net.*

fun main() {
    try {
        Socket("localhost", 8001).use { socket -> 
            FileOutputStream("client_recv.txt").use { fos ->
                FileInputStream("client_send.txt").use {  fis ->

                    var ch = 0

                    // client_send.txtの内容をサーバに送信
                    val output = socket.getOutputStream()

                    do {
                        ch = fis.read()
                        if(ch == -1) break
                        output.write(ch)
                    } while (true)

                    // 終了を示すため0を送信
                    output.write(0)

                    // サーバーからの返信をclient_recv.txtに出力
                    val input = socket.getInputStream()

                    do {
                        ch = input.read()
                        if(ch == -1) break
                        fos.write(ch)
                    } while (true)

                }

            }
        }
    }
    catch(e : Exception) {
        println(e.toString())
    }
}
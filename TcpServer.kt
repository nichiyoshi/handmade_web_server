import java.io.*
import java.net.*

fun main() {
    try {
        ServerSocket(8001).use { server -> 
            FileOutputStream("server_recv.txt").use { fos ->
                FileInputStream("server_send.txt").use {  fis ->

                    println("クライアントからの接続を待ちます")
                    val socket = server.accept()
                    println("クライアント接続")

                    var ch = 0

                    // クライアントから受け取った内容をserver_recv.txtに出力
                    val input = socket.getInputStream()

                    // クライアントは、終了のマークとして0を送付してくる
                    do {
                        ch = input.read()
                        if ( ch == 0 ) break
                        fos.write(ch)
                    } while (true)

                    // server_send.txtの内容をクライアントに送付
                    val output = socket.getOutputStream()

                    do {
                        ch = fis.read()
                        if(ch == -1) break
                        output.write(ch)
                    } while (true)

                    socket.close()
                    println("通信を終了しました")

                }

            }
        }
    }
    catch(e : Exception) {
        println(e.toString())
    }
}
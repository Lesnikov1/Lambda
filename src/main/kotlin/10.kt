data class Message(
    val text: String,
    var read: Boolean
)

data class Chat(
    val messages: MutableList<Message> = mutableListOf()
)

object ChatService {

    private val chats: MutableMap<Int, Chat> = mutableMapOf()

    class NoSuchChatException(message: String) : Exception(message)
    class NoMessageException(message: String) : Exception(message)

    fun clear() = chats.clear()

    fun getChats() = chats.forEach { println(it) }

    fun addMessage(userId: Int, message: Message) = chats.getOrPut(userId) { Chat() }.messages.add(message)

    fun getUnreadChatsCount() = chats.values.count { chat -> chat.messages.any { !it.read } }

    fun getLastMessages() =
        chats.values.asSequence().mapNotNull { it.messages.lastOrNull()?.text }.toList().takeIf { it.isNotEmpty() }
            ?: throw NoMessageException("нет сообщений...")

    fun deleteChat(userId: Int) = chats.remove(userId) ?: throw NoSuchChatException("нет чата...")

    fun getMessages(userId: Int, count: Int): List<Message>  {
        //по count можно поймать IllegalArgumentException или проверка на отрицательное число???
        val chat = chats[userId] ?: throw NoSuchChatException("нет чата...")
        return chat.messages.takeLast(count).onEach { it.read = true }
    }

    fun deleteMessage(userId: Int, indexMessage: Int) {
        val chat = chats[userId] ?: throw NoSuchChatException("нет чата...")
        try {
            chat.messages.removeAt(indexMessage)
        } catch (exception: IndexOutOfBoundsException) {
            throw NoMessageException("нет сообщения с индексом $indexMessage...")
        }
    }

    fun chatExists(userId: Int) = chats.containsKey(userId)
    fun messagesExists(userId: Int) = chats[userId]?.messages?.any()

}

fun main() {
//    ChatService.addMessage(1, Message("text11", true))
//    ChatService.addMessage(1, Message("text12", true))
//    ChatService.addMessage(1, Message("text13", true))
//    ChatService.addMessage(2, Message("text21", false))
//    ChatService.addMessage(2, Message("text22", false))
//    ChatService.getChats()
//    println(ChatService.getUnreadChatsCount())
//    println(ChatService.getLastMessages())
//    println(ChatService.getChats())
//    ChatService.deleteMessage(2, 1)
//    ChatService.deleteChat(1)
//    ChatService.getChats()
//    println(ChatService.messagesExists(1))
}
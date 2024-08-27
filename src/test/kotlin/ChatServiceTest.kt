import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    @Before
    fun clearBeforeTest() = ChatService.clear()

    @Test
    fun getLastFindMessages() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.addMessage(1, Message("12", false))
        ChatService.addMessage(2, Message("21", false))
        ChatService.addMessage(2, Message("22", false))
        assertEquals("[12, 22]", ChatService.getLastMessages().toString())
    }

    @Test(expected = ChatService.NoMessageException::class)
    fun getLastNotFindMessages() {
        //ChatService.addMassage(1, Message("11", false))
        ChatService.getLastMessages()
    }

    @Test(expected = ChatService.NoMessageException::class)
    fun getLastNotFindMassages2() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.deleteMessage(1, 0)
        ChatService.getLastMessages()
    }

    @Test
    fun deleteFindChat() {
        ChatService.addMessage(1, Message("11", false))
        assertEquals(true, ChatService.chatExists(1))
        ChatService.deleteChat(1)
        assertEquals(false, ChatService.chatExists(1))
    }

    @Test(expected = ChatService.NoSuchChatException::class)
    fun deleteNotFindChat() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.deleteChat(2)
    }

    @Test
    fun getFindMessages() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.addMessage(1, Message("12", false))
        val result = ChatService.getMessages(1, 2).toString()
        val expected = "[Message(text=11, read=true), Message(text=12, read=true)]"
        assertEquals(expected, result)
    }

    @Test(expected = ChatService.NoSuchChatException::class)
    fun getFindNotMessages() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.getMessages(2, 1)
    }

    @Test
    fun deleteMessageFindAll() {
        ChatService.addMessage(1, Message("11", false))
        assertEquals(true, ChatService.messagesExists(1))
        ChatService.deleteMessage(1, 0)
        assertEquals(false, ChatService.messagesExists(1))
    }

    @Test(expected = ChatService.NoMessageException::class)
    fun deleteNotFindMessageFindChat() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.deleteMessage(1, 1)
    }

    @Test(expected = ChatService.NoSuchChatException::class)
    fun deleteMessageNotFindChat() {
        ChatService.addMessage(1, Message("11", false))
        ChatService.deleteMessage(2, 0)
    }
}
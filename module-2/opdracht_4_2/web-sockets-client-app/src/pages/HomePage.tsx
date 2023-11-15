import {StoreTypes} from "../data/DataStore.ts";
import {useSelector} from "react-redux";
import {EventTypes, Message, WSSendEvent} from "../data/Types.ts";
import useWebSocket from "react-use-websocket";
import {useState} from "react";

export function HomePage() {
  const messages = useSelector<StoreTypes, StoreTypes["messages"]>(s => s.messages);
  const currentUser = useSelector<StoreTypes, StoreTypes["currentUser"]>(s => s.currentUser);
  const [newMessageText, setNewMessageText] = useState<string>("");

  const { sendJsonMessage} = useWebSocket(import.meta.env.VITE_WS_URI, {share: true});

  const onSendMessage = () => {
    sendJsonMessage<WSSendEvent<Message>>({
      action: "sendmessage",
      data: {
        type: EventTypes.NewMessageEvent,
        content: {
          date: new Date().getTime(),
          message: newMessageText,
          sender: currentUser
        }
      }
    });
    setNewMessageText("");
  };

  return (
    <div>
      <h1>Fun chat app</h1>
      <form onSubmit={e => {
        e.preventDefault();
        onSendMessage();
      }}>
        <label htmlFor="new-message">New message</label>
        <input
          id="new-message"
          type="text"
          value={newMessageText}
          onChange={e => setNewMessageText(e.target.value)}
        />
        <button onClick={onSendMessage}>Send message</button>
      </form>
      <div>
        {messages.map(v => <div key={v.date}>{v.message}</div>)}
      </div>
    </div>
  );
}
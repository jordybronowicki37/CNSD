import {StoreTypes} from "../data/DataStore.ts";
import {useDispatch, useSelector} from "react-redux";
import {EventTypes, Message, WSSendEvent} from "../data/Types.ts";
import {FormEvent, useContext, useEffect, useRef, useState} from "react";
import {MessageItem} from "../components/MessageItem.tsx";
import {WebSocketContext} from "../data/webSocket/WebSocketContext.ts";
import {ConnectionStatus} from "../components/ConnectionStatus.tsx";
import "./HomePage.scss";
import {currentUserSetAction} from "../data/reducers/CurrentUserReducer.ts";

export function HomePage() {
  const dispatch = useDispatch();
  const webSocket = useContext(WebSocketContext);
  const messages = useSelector<StoreTypes, StoreTypes["messages"]>(s => s.messages);
  const currentUser = useSelector<StoreTypes, StoreTypes["currentUser"]>(s => s.currentUser);
  const [newMessageText, setNewMessageText] = useState<string>("");
  const [loginUserName, setLoginUserName] = useState<string>("");
  const loginModal = useRef<HTMLDialogElement | null>(null);

  const onSendMessage = () => {
    webSocket?.sendJsonMessage<WSSendEvent<Message>>({
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

  const onLogin = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    dispatch(currentUserSetAction(loginUserName));
    loginModal.current?.close();
  }

  useEffect(() => {
    if (loginModal.current !== null) {
      loginModal.current?.showModal();
    }
  }, [loginModal]);

  return (
    <div className="home-page">
      <header>
        <h1>Fun chat app</h1>
        <ConnectionStatus/>
      </header>
      <div className="messages-list">
        {messages.map(v => <MessageItem key={v.date} message={v} byCurrentUser={v.sender === currentUser} />)}
      </div>
      <form autoComplete="off" onSubmit={e => {
        e.preventDefault();
        onSendMessage();
      }}>
        <label htmlFor="new-message" hidden>New message</label>
        <input
          id="new-message"
          placeholder="Write a message"
          type="text"
          value={newMessageText}
          onChange={e => setNewMessageText(e.target.value)}
        />
        <button type="submit">Send message</button>
      </form>

      <dialog
        ref={loginModal}
        className="login-modal"
        onCancel={e => e.preventDefault()}>
        <h2>Who is you?</h2>
        <form onSubmit={onLogin} autoComplete="off">
          <label htmlFor="username">Username</label>
          <input
            id="username"
            type="text"
            value={loginUserName}
            onChange={e => setLoginUserName(e.target.value)}
          />
          <button type="submit">Login</button>
        </form>
      </dialog>
    </div>
  );
}
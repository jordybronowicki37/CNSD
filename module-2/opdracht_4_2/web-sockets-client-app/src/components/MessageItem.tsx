import {Message} from "../data/Types.ts";
import "./MessageItem.css";

export type MessageItemProps = {
  message: Message,
  byCurrentUser: boolean,
}

export function MessageItem({message, byCurrentUser}: MessageItemProps) {
  const date = new Date(message.date);

  return (
    <div className={`message-item ${byCurrentUser && 'message-by-current-user'}`}>
      <div className="message-top">
        <b>{message.sender}</b>
        <div>{date.getHours()}:{date.getMinutes()}</div>
      </div>
      <div>{message.message}</div>
    </div>
  );
}

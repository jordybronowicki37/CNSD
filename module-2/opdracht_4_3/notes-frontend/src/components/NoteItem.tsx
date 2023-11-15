import "./NoteItem.scss";
import {Note} from "../data/Types";

export type NoteItemProps = {
  note: Note,
}

export function NoteItem({note}: NoteItemProps) {
  return (
    <div className="note-item">
      <div className="note-top">
        <div className="note-owner">{note.owner}</div>
        <div>{formatDate(note.created)}</div>
      </div>
      <div className="note-content">
        {note.body}
      </div>
    </div>
  )
}

function formatDate(date: Date): string {
  function pad(n: number){ return n<10 ? '0'+n : n }
  return pad(date.getHours()) + ':' + pad(date.getMinutes())
}

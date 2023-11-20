import "./OverviewPage.scss";
import {NoteItem} from "../components/NoteItem.tsx";
import {useDispatch, useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {FormEvent, useContext, useEffect, useRef, useState} from "react";
import {currentUserSetAction} from "../data/reducers/CurrentUserReducer.ts";
import {WebSocketContext} from "../data/webSocket/WebSocketContext.ts";
import {fetchNotes} from "../data/requester/NotesRequester.ts";
import {CreateNoteEvent} from "../data/webSocket/WebSocketActions.ts";

export function OverviewPage() {
  const webSocket = useContext(WebSocketContext);
  const loginDialog = useRef<HTMLDialogElement | null>(null);
  const [username, setUsername] = useState<string>("");

  const currentUser = useSelector<StoreTypes, StoreTypes["currentUser"]>(s => s.currentUser);
  const notes = useSelector<StoreTypes, StoreTypes["notes"]>(s => s.notes);
  const dispatch = useDispatch();

  useEffect(() => {
    if(loginDialog.current === null) return;
    if(currentUser !== "") return;
    if(loginDialog.current?.open) return;
    loginDialog.current?.showModal();
  }, [loginDialog, currentUser]);

  useEffect(() => {
    if(currentUser === "") return;
    fetchNotes();
  }, [currentUser]);

  const logoutHandler = () => {
    setUsername("");
  }

  const loginHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if(username === "") return;
    dispatch(currentUserSetAction(username));
    loginDialog.current?.close();
  }

  const createNewNoteHandler = () => {
    webSocket?.sendJsonMessage(CreateNoteEvent("Hello world"))
  }

  return (
    <div className="overview-page">
      <header>
        <h1>Notes app</h1>
        <div className="current-user" hidden={currentUser !== ''}>
          <div>Welcome</div>
          <div>{currentUser}</div>
          <button
            type="button"
            onClick={logoutHandler}
          >
            Logout
          </button>
        </div>
      </header>

      <button onClick={createNewNoteHandler}>Create new note</button>

      <div className="notes">
        {notes.map(v => <NoteItem key={v.Id} note={v} />)}
      </div>

      <dialog ref={loginDialog} className="login-dialog">
        <h2>Login</h2>
        <form onSubmit={loginHandler}>
          <label htmlFor="username">Username</label>
          <input id="username" name="username" type="text" value={username} onChange={e => setUsername(e.target.value)}/>
          <button type="submit">Login</button>
        </form>
      </dialog>
    </div>
  )
}
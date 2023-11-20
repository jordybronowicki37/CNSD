import {Store} from "../DataStore.ts";
import {Note} from "../Types.ts";
import {notesAddAction, notesEditAction, notesRemoveAction, notesSetAction} from "../reducers/NotesReducer.ts";

export async function fetchNotes() {
  const user = Store.getState().currentUser;
  if (user === null) return;

  await fetch(`users/${user}/notes`)
    .then(response => {
      if (!response.ok) throw new Error("Request failed");
      return response.json();
    })
    .then(data => {
      const notes = data as Note[];
      Store.dispatch(notesSetAction(notes));
    })
}

export async function createNote(text: string) {
  const user = Store.getState().currentUser;
  if (user === null) return;

  await fetch(`users/${user}/notes`, {
      method: "post",
      body: JSON.stringify({text}),
      headers: {"Content-Type": "application/json"}
  })
    .then(response => {
      if (!response.ok) throw new Error("Request failed");
      return response.json();
    })
    .then(data => {
      const note = data as Note;
      Store.dispatch(notesAddAction(note));
    })
}

export async function updateNote(noteId: string, text: string) {
  const user = Store.getState().currentUser;
  if (user === null) return;

  await fetch(`users/${user}/notes/${noteId}`, {
    method: "put",
    body: JSON.stringify({text}),
    headers: {"Content-Type": "application/json"}
  })
    .then(response => {
      if (!response.ok) throw new Error("Request failed");
      return response.json();
    })
    .then(data => {
      const note = data as Note;
      Store.dispatch(notesEditAction(note));
    })
}

export async function deleteNote(noteId: string) {
  const user = Store.getState().currentUser;
  if (user === null) return;

  await fetch(`users/${user}/notes/${noteId}`, {
    method: "delete",
  })
    .then(response => {
      if (!response.ok) throw new Error("Request failed");
      return response.json();
    })
    .then(() => {
      Store.dispatch(notesRemoveAction({Id: noteId}));
    })
}

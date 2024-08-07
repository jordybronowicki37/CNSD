import {createAction, createReducer} from "@reduxjs/toolkit";
import {Note} from "../Types.ts";

export const notesSetAction = createAction<Note[]>("notes/set");
export const notesAddAction = createAction<Note>("notes/add");
export const notesEditAction = createAction<Note>("notes/edit");
export const notesRemoveAction = createAction<Pick<Note, "Id">>("notes/remove");

export const notesReducer = createReducer<Note[]>([], builder => {
  builder
    .addCase(notesSetAction, (_, action) => [...action.payload])
    .addCase(notesAddAction, (state, action) => [...state, action.payload])
    .addCase(notesEditAction, (state, action) => {
      const note = state.find(n => n.Id === action.payload.Id);
      if(note !== undefined) Object.assign(note, action.payload);
      return state;
    })
    .addCase(notesRemoveAction, (state, action) =>
        state.filter(n => !(n.Id === action.payload.Id)))
})

import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {Note} from "./Types.ts";
import {notesReducer} from "./reducers/NotesReducer.ts";

export type StoreTypes = {
  notes: Note[],
}

const rootReducer = combineReducers<StoreTypes>({
  notes: notesReducer,
});

export const Store = configureStore({reducer: rootReducer});

import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {Note} from "./Types.ts";
import {notesReducer} from "./reducers/NotesReducer.ts";
import {currentUserReducer} from "./reducers/CurrentUserReducer.ts";

export type StoreTypes = {
  notes: Note[],
  currentUser: string,
}

const rootReducer = combineReducers<StoreTypes>({
  notes: notesReducer,
  currentUser: currentUserReducer,
});

export const Store = configureStore({reducer: rootReducer});

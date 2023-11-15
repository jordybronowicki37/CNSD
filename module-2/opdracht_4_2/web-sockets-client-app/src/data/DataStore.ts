import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {Message} from "./Types.ts";
import {messagesReducer} from "./reducers/MessagesReducer.ts";
import {currentUserReducer} from "./reducers/CurrentUserReducer.ts";

export type StoreTypes = {
  messages: Message[],
  currentUser: string,
}

const rootReducer = combineReducers<StoreTypes>({
  messages: messagesReducer,
  currentUser: currentUserReducer,
});

export const Store = configureStore({reducer: rootReducer});

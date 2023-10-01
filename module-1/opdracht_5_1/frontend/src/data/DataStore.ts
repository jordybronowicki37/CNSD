import {Account, Holder} from "./Types.ts";
import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {accountReducer} from "./reducers/AccountReducer.ts";
import {holderReducer} from "./reducers/HolderReducer.ts";

export type StoreTypes = {
  accounts: Account[],
  holders: Holder[],
}

const rootReducer = combineReducers({
  accounts: accountReducer,
  holders: holderReducer
});

export const Store = configureStore({reducer: rootReducer});

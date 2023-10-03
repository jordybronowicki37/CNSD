import {Account, Holder, User} from "./Types.ts";
import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {accountReducer} from "./reducers/AccountReducer.ts";
import {holderReducer} from "./reducers/HolderReducer.ts";
import {userReducer} from "./reducers/UserReducer.ts";

export type StoreTypes = {
  accounts: Account[];
  holders: Holder[];
  user: User | null;
}

const rootReducer = combineReducers({
  accounts: accountReducer,
  holders: holderReducer,
  user: userReducer,
});

export const Store = configureStore({reducer: rootReducer});

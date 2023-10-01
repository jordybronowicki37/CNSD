import {createAction, createReducer} from "@reduxjs/toolkit";
import {User} from "../Types.ts";

export const userLoginAction = createAction<User>("user/login");
export const userLogoutAction = createAction("user/logout");
const user: User = {username: "jordy"}
export const userReducer = createReducer<User | null>(user, builder => {
  builder
    .addCase(userLoginAction, (_, action) => action.payload)
    .addCase(userLogoutAction, () => null)
})

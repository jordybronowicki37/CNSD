import {userLoginAction} from "../reducers/UserReducer.ts";
import {Store} from "../DataStore.ts";
import {User} from "../Types.ts";

export async function loginUser(username: string, password: string) {
    const response = await fetch(`/api/login`, {
        body: JSON.stringify({username, password}),
        method: "POST",
        headers: {"Content-Type": "application/json"},
    });
    if (!response.ok) throw new Error("Login failed");
    const json = await response.json() as User;
    Store.dispatch(userLoginAction(json));
}
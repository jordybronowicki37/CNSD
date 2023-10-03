import {userLoginAction} from "../reducers/UserReducer.ts";
import {Store} from "../DataStore.ts";
import {User} from "../Types.ts";

export async function loginUser(userName: string, password: string) {
    // TODO use api
    // const response = await fetch(`/api/rekeningen?user=${user.id}`);
    // const json = await response.json();

    await new Promise(resolve => setTimeout(resolve, 1000));
    if (password === "hunter2") throw new Error("Login failed");

    const user: User = {
        id: 1,
        naam: userName,
        bsn: ""
    }
    Store.dispatch(userLoginAction(user));
}
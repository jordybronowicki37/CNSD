import {userLoginAction} from "../reducers/UserReducer.ts";
import {Store} from "../DataStore.ts";
import {User} from "../Types.ts";
import axios from "axios";

export async function loginUser(username: string, password: string) {
    await axios.post(
      `/api/login`,
      {username, password}
    )
      .then(response => {
          const user = response.data as User;
          Store.dispatch(userLoginAction(user));
      })
      .catch(() => {
          throw new Error("Login failed");
      });
}

export async function signupUser(username: string, bsn: string) {
  await axios.post(
    `/api/persoon`,
    {naam: username, bsn}
  )
    .then(response => {
      const user = response.data as User;
      Store.dispatch(userLoginAction(user));
    })
    .catch(() => {
      throw new Error("Signup failed");
    });
}

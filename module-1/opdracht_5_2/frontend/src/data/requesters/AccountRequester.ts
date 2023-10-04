import {Store} from "../DataStore.ts";
import {accountAddAction, accountsSetAction} from "../reducers/AccountReducer.ts";
import {Account} from "../Types.ts";
import axios from "axios";

export async function getAccounts() {
  const user = Store.getState().user;
  if (user === null) return;

  await axios.get(`/api/rekening?persoonId=${user.id}`)
    .then(response => {
      const accounts = response.data as Account[];
      Store.dispatch(accountsSetAction(accounts));
    })
    .catch(() => {
      throw new Error("Accounts fetch failed");
    });
}

export async function createAccount() {
  const user = Store.getState().user;
  if (user === null) return;

  await axios.post(`/api/rekening`, {persoonId: user.id})
    .then(response => {
      const account = response.data as Account;
      Store.dispatch(accountAddAction(account));
    })
    .catch(() => {
      throw new Error("Account create failed");
    });
}

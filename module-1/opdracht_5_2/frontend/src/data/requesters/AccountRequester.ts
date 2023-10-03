import {Store} from "../DataStore.ts";
import {accountAddAction, accountsSetAction} from "../reducers/AccountReducer.ts";
import {Account} from "../Types.ts";

export async function getAccounts() {
  const user = Store.getState().user;
  if (user === null) return;

  const response = await fetch(`/api/rekening?persoonId=${user.id}`);
  if (!response.ok) throw new Error("Accounts fetch failed");
  const json = await response.json() as Account[];
  Store.dispatch(accountsSetAction(json));
}

export async function createAccount() {
  const user = Store.getState().user;
  if (user === null) return;

  const response = await fetch(`/api/rekening`, {
    body: JSON.stringify({persoonId: user.id}),
    method: "POST",
    headers: {"Content-Type": "application/json"},
  });
  if (!response.ok) throw new Error("Account create failed");
  const json = await response.json() as Account;
  Store.dispatch(accountAddAction(json));
}

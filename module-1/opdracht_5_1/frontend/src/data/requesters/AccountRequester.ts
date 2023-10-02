import {Store} from "../DataStore.ts";
import {accountsSetAction} from "../reducers/AccountReducer.ts";
import {Account, AccountStatus} from "../Types.ts";

// TODO remove initial data
const accounts: Account[] = [
  {
    id: 1,
    iban: "NL99CNSD1234567890",
    saldo: 546.95,
    status: AccountStatus.NORMAL,
    personen: [1]
  },
  {
    id: 2,
    iban: "NL99CNSD0987654321",
    saldo: -96.42,
    status: AccountStatus.BLOCKED,
    personen: [2, 1]
  }
]

export async function GetAccounts() {
  const user = Store.getState().user;
  if (user === null) return;

  // TODO use api
  // const response = await fetch(`/api/rekeningen?user=${user.id}`);
  // const json = await response.json();
  await new Promise(resolve => setTimeout(resolve, 500));
  Store.dispatch(accountsSetAction(accounts));
}

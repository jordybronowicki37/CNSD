import {Store} from "../DataStore.ts";
import {Holder} from "../Types.ts";
import {holdersSetAction} from "../reducers/HolderReducer.ts";
import axios from "axios";

export async function getHolders() {
  const user = Store.getState().user;
  if (user === null) return;

  await axios.get(`/api/persoon`)
    .then(response => {
      const holders = response.data as Holder[];
      Store.dispatch(holdersSetAction(holders));
    })
    .catch(_ => {
      throw new Error("Holders fetch failed");
    });
}

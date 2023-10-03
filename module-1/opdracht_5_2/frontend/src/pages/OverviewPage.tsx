import "./OverviewPage.scss";
import {AccountItem} from "../components/AccountItem.tsx";
import {useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {Holder} from "../data/Types.ts";
import {useEffect, useState} from "react";
import {createAccount, getAccounts} from "../data/requesters/AccountRequester.ts";
import {getHolders} from "../data/requesters/HolderRequester.ts";
import {useCheckForUserNotLoggedIn} from "../hooks/CheckLoginHook.ts";

export function OverviewPage() {
  useCheckForUserNotLoggedIn();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [newAccountLoading, setNewAccountLoading] = useState(false);
  const accounts = useSelector<StoreTypes, StoreTypes["accounts"]>(s => s.accounts);
  const holders = useSelector<StoreTypes, StoreTypes["holders"]>(s => s.holders);

  useEffect(() => {
    const holdersPromise = getHolders();
    const accountsPromise = getAccounts();
    Promise.all([holdersPromise, accountsPromise])
      .then(_ => {
        setLoading(false);
      })
      .catch(_ => {
        setLoading(false);
        setError(true);
      });
  }, []);

  const onCreateAccount = () => {
    setNewAccountLoading(true);
    createAccount().then(_ => {
      setNewAccountLoading(false)
    });
  }

  return (
    <div className="overview-page">
      <div className="overview-card">
        <div className="overview-header">
          <h2>Overview</h2>
          <div className="overview-total-container">
            <p>Total saldo:</p>
            <p className="total-saldo">EUR {accounts.map(v => v.saldo).reduce((pv, cv) => pv + cv, 0).toFixed(2)}</p>
          </div>
        </div>
        <div hidden={!loading}>
          Loading...
        </div>
        <div hidden={!error}>
          Something went wrong!
        </div>
        <div className="accounts-container" hidden={loading || error}>
          {accounts.map(v => <AccountItem key={v.id} account={v} title={findHolderName(v.personen[0], holders)}/>)}
        </div>
        <div>
          <button
            disabled={newAccountLoading}
            onClick={onCreateAccount}
            className="create-new-account"
            hidden={loading || error}>Create new account</button>
        </div>
      </div>
    </div>
  );
}

function findHolderName(id: number, list: Holder[]): string {
  const holder = list.find(v => v.id === id)!;
  if (holder === undefined) return "";
  return holder.naam;
}

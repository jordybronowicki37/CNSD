import "./AccountItem.scss";
import {Account, AccountStatus} from "../data/Types.ts";

export type AccountItemProps = {
  account: Account;
}

export function AccountItem({ account }: AccountItemProps) {
  return (
    <div className="account-item">
      <div>
        <div className="account-title">
          <p className="account-name">JR Test</p>
          {account.status === AccountStatus.BLOCKED && <p className="account-blocked">Blocked</p>}
        </div>
        <p className="account-iban">{account.iban}</p>
      </div>
      <div>
        <p className={`account-saldo ${account.saldo < 0 ? "test" : ""}`}>EUR {account.saldo.toFixed(2)}</p>
      </div>
    </div>
  );
}

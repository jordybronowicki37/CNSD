export type Account = {
  id: number;
  iban: string;
  saldo: number;
  status: AccountStatus;
  personen: number[];
}

export enum AccountStatus {
  NORMAL = "NORMAAL",
  BLOCKED = "GEBLOKKEERD",
}

export type Holder = {
  id: number;
  bsn: string;
  naam: string;
  rekeningen: number[];
}

import {createContext} from "react";
import {WebSocketHook} from "react-use-websocket/dist/lib/types";
import {WSSendEvent} from "../Types.ts";

export const WebSocketContext = createContext<WebSocketHook<WSSendEvent<any>, MessageEvent<any> | null> | null>(null);

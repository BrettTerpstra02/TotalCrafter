package com.brett.voxel.networking;

/**
*
* @author brett
* @date May 31, 2020
*/

public class PACKETS {
	
	// here are all the packet IDs that can be send or received.
	public static final byte LOGIN = 0x1;
	public static final byte ID = 0x2;
	public static final byte CHUNKREQ = 0x3;
	public static final byte BLOCKMOD = 0x4;
	public static final byte POSSYNC = 0x5;
	public static final byte DISCONNECT = 0x6;
	public static final byte CHUNKMOD = 0x7;
	public static final byte EXIT = 0x8;
	public static final byte CLIENTSYNC = 0x9;
	
}

package me.ixek.hellupdeathmessages.enums;

public enum VersionEnums {

	VERSION_18(0), // version 1.8 (minimum allowed ver)
	VERSION_19(1), // version 1.9
	VERSION_110(2), // version 1.10
	VERSION_111(3), // version 1.11
	VERSION_112(4), // version 1.12
	VERSION_113(5), // version 1.13
	VERSION_114(6), // version 1.14
	VERSION_115(7), // version 1.15
	VERSION_116(8), // version 1.16
	VERSION_117(9), // version 1.17 (only here to disallow plugin being enabled until I have support)
	VERSION_118(10),
	VERSION_119(11),

	OTHER_VERSION(-1); // unknown version, plugin disallowed to run on server
	
    private int version;
    
    VersionEnums(int versionInt) 
    {
        this.version = versionInt;
    }
 
    public int getVersionInt() 
    {
        return version;
    }
	
}

import meggy.Meggy;

class OneCond {
	public static void main(String[] s) {
	    if (-4  == ((byte)1-5))
		  Meggy.setPixel( (byte)2, (byte)1, Meggy.Color.GREEN );
	    else
		 Meggy.setPixel( (byte)2, (byte)1, Meggy.Color.RED );
	
	    if (-4  == (-9+(byte)5))
		  Meggy.setPixel( (byte)2, (byte)1, Meggy.Color.GREEN );
	    else
		 Meggy.setPixel( (byte)2, (byte)1, Meggy.Color.RED );
	}
}

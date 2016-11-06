package br.eti.francisco.base64;

import java.io.UnsupportedEncodingException;

public class Base64Manual {

	private static final byte[] URL_SAFE_ENCODE_TABLE = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
	};

	private static int calculateSize(int [] data){
		int len = data.length * 8;
		len = len + len % 6;
		return len / 6; 
	}
	
	private static void convert(byte [] enc){
		for (int i = 0; i < enc.length; i++) {
			enc[i] = URL_SAFE_ENCODE_TABLE[enc[i]];
		}
	}
	
	public static int[] convertToIntArray(byte[] input){
	    int[] ret = new int[input.length];
	    for (int i = 0; i < input.length; i++){
	        ret[i] = input[i] & 0xff; // Range 0 to 255, not -128 to 127
	    }
	    return ret;
	}	
	
	private static byte[] encode(int [] data){
		byte [] result = new byte[calculateSize(data)];
		int j = 0;
		byte res = 0;
		int resSize = 0;
		for (int i = 0; i < result.length; i++) {
			if(j >= data.length){
				result[i] =  (byte) ((res << (6 - resSize)) & 0x3F);
				break;
			}
			int d = data[j];
			if(resSize == 0){
				result[i] = (byte)(d >> 2);
				resSize = 2;
				res = (byte) (d & 0x03);
				j++;
			}else if(resSize == 2){
				result[i] = (byte) (res << 4 | d >> 4);
				resSize = 4;
				res = (byte) (d & 0x0F);
				j++;
			}else if(resSize == 4){
				result[i] = (byte) (res << 2 | d >> 6);
				resSize = 6;
				res = (byte) (d & 0x3F);
			}else if(resSize == 6){
				result[i] = res;
				resSize = 0;
				res = 0;
				j++;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			String text = "te!@#á";
			int [] data = convertToIntArray(text.getBytes("UTF-8"));
			byte [] encoded = encode(data);
			convert(encoded);
			System.out.println(new String(encoded));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}

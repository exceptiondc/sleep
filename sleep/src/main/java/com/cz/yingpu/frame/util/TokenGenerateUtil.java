package com.cz.yingpu.frame.util;

import java.util.UUID;

/**
 * Created by lenovo on 2017/3/23.
 */
public class TokenGenerateUtil {

    public static String getToken(){
        UUID uuid = UUID.randomUUID();
        String token = compressedUUID(uuid);
        //验证是否有相同的Token,为true重新生成
        uuid = UUID.randomUUID();
        token = compressedUUID(uuid);
        return token;
    }

    //对UUID进行处理，形成想要的Token长度
    private static String compressedUUID(UUID uuid) {
        byte[] byUuid = new byte[SystemConfigConstants.getInt("TOKEN_BYTE_LEN")];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, SystemConfigConstants.getInt("TOKEN_BYTE_LEN")/2);
        String compressUUID = Base64.encodeBase64URLSafeString(byUuid);
        return compressUUID;
    }

    //长度处理
    private static void long2bytes(long value, byte[] bytes, int offset) {
        for (int i = SystemConfigConstants.getInt("TOKEN_BYTE_LEN")/2-1; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }


}

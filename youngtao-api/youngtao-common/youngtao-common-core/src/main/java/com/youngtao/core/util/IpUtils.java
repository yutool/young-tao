package com.youngtao.core.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * @author ankoye@qq.com
 * @date 2020/11/15
 */
public class IpUtils {

    public static final String IP_UNKNOWN = "unknown ip";

    /**
     * 获取本机IP
     * @return 本机IP
     */
    public static String getLocalIp() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win") ? getLocalIpFromWindows() : getLocalIpFromLinux();
    }

    public static String getMacAddr() {
        String MacAddress = "";
        StringBuilder str = new StringBuilder();

        try {
            NetworkInterface nic = NetworkInterface.getByName("eth0");
            byte[] buf = nic.getHardwareAddress();
            int length = buf.length;

            for (int i = 0; i < length; ++i) {
                byte aBuf = buf[i];
                str.append(byteHEX(aBuf));
            }

            MacAddress = str.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return MacAddress;
    }

    /**
     * 获取Linux服务器的本地IP
     * @return IP
     */
    private static String getLocalIpFromLinux() {
        String ip = IP_UNKNOWN;

        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();

            while (enumeration.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) enumeration.nextElement();
                if (ni.getName().equals("eth0")) {
                    Enumeration addresses = ni.getInetAddresses();
                    while (true) {
                        if (!addresses.hasMoreElements()) {
                            break;
                        }

                        InetAddress ia = (InetAddress) addresses.nextElement();
                        if (!(ia instanceof Inet6Address)) {
                            ip = ia.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (IP_UNKNOWN.equals(ip)) {
            ip = getLocalIpFromWindows();
        }

        return ip;
    }

    private static String byteHEX(byte ib) {
        char[] Digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[]{Digit[ib >>> 4 & 15], Digit[ib & 15]};
        return new String(ob);
    }

    /**
     * 获取Windows服务器的本地IP
     * @return IP
     */
    private static String getLocalIpFromWindows() {
        String localIp;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localIp = IP_UNKNOWN;
        }

        return localIp;
    }

    /**
     * 获取主机名
     * @return 主机名
     */
    public static String getHostName() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostName = "hostname";
        }
        return hostName;
    }

    /**
     * 获取客户端IP
     * @param request ServerHttpRequest
     * @return 客户端IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null, unknown = "unknown", seperator = ",";
        int maxLength = 15;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 使用代理，则获取第一个IP地址
        if (StringUtils.isEmpty(ip) && ip.length() > maxLength) {
            int idx = ip.indexOf(seperator);
            if (idx > 0) {
                ip = ip.substring(0, idx);
            }
        }

        return ip;
    }
//
//    /**
//     * 获取IP+主机名
//     * @return [IP+,+主机名]
//     */
//    public static String getIpWithBracketWrap() {
//        return FormatUtils.wrapStringWithBracket(getLocalIp() + "," + getHostName());
//    }
//
//    public static void main(String[] args) {
//        System.out.println(getLocalIpFromWindows());
//        System.out.println(getLocalIpFromLinux());
//    }
}

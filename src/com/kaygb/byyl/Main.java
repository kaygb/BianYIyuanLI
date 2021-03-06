package com.kaygb.byyl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static boolean breakLen(int i, int len) { // 判断数组是否越界
        return i < len;
    }
    private static String error() { // 错误
        return "error";
    }
    private static void prtln(Object obj){
        System.out.println(obj);
    }
    private static void prt(Object obj){
        System.out.print(obj);
    }
    private static void ForBL(ArrayList<Object> arrayList,String msg){
        for (Object i:arrayList){
            prt(i + msg);
        }
    }
    private static void ForBL(Object[] objects,String msg){
        for (Object i:objects){
            prt(i + msg);
        }
    }
    public static void main(String[] args) {

        String[] k = {"begin", "while", "for", "if", "else", "long", "case", "do", "main", "int"};
        String[] p = {"<=", "<>", ":=", "<", "(", "*",  ":", "+", ")", ";", ","};
        Map<String, String> map = new HashMap<String,String>();
        ArrayList<String> ct = new ArrayList<>();
        ArrayList<String> it = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("------------");
            System.out.print("K: ");
            for (String i:k){
                System.out.print(i+ " ");
            }
//            ForBL(k , " ");
            System.out.println("\n----------");
            System.out.print("P: ");
            for (String i:p){
                System.out.print(i+ " ") ;
            }
            if (!ct.isEmpty()){
                System.out.println("\n----------");
                System.out.print("c: ");
                for (String cc:ct){
                    System.out.print(cc+ " ") ;
                }
            }
            if (!it.isEmpty()){
                System.out.println("\n----------");
                System.out.print("i: ");
                for (String ii:it){
                    System.out.print(ii+ " ") ;
                }
            }
            System.out.println("\n----------");
            System.out.println("\nPlease input:");
            String str = scanner.nextLine();
            if (str.length() == 0){
                break;
            }
            String[] res = lexical(str,k,p);
            for (String re : res) {
                if (re==null){
                    break;
                }
                int c=0,i=0;
                int flag = 0;
                for (int pp = 0;pp <p.length;pp++){
                    if (p[pp].equals(re)){
                        map.put("(p,"+pp+")" ,p[pp]);
                        System.out.println("(p,"+pp+")" + "\nthe word is: " + p[pp]);
                        flag=1;
                    }
                }
                if(flag==0){
                    for (int kk = 0;kk <k.length;kk++){
                        if (k[kk].equals(re)){
                            map.put("(k,"+kk+")" ,k[kk]);
                            System.out.println("(k,"+kk+")" + "\nthe word is: " + k[kk]);
                        }
                    }
                }
                if(flag==0){
                    if (re.length()!=0 && re.charAt(0) >= '0' && re.charAt(0) <= '9' ){
                        map.put("(c,"+(it.size())+")" ,re);
                        System.out.println("(c,"+ (ct.size())+")" + "\nthe word is: " + re);
                        ct.add(re);
                    }
                }
                if(flag==0){
                    if (re.length()!=0 &&re.charAt(0) >= 'a' && re.charAt(0) <= 'z' ){
                        map.put("(i,"+(it.size())+")" ,re);
                        System.out.println("(i," +(it.size()) +")" + "\nthe word is: " + re);
                        it.add(re);
                    }
                }

            }
        }
    }
    private static String[] lexical(String str , String[] ktable ,String[] ptable){
        String newstr = str.replace(" ", "");
        String[] res = new String[100];
        int res_point = 0; // 初始化
        for (int i = 0; i < newstr.length(); i++) {
            char ca = newstr.charAt(i);
            if (newstr.length() == 1){
                res[res_point] = ca+"";
                break;
            }
            if (ca >= 'a' && ca <= 'z') { // 处理首字母是字符的字符串
                res[res_point] = "";
                res[res_point] += ca;
                if (!breakLen(i+1, newstr.length())) {
                    break;
                }
                i++;
                ca = newstr.charAt(i);
                while (ca >= 'a' && ca <= 'z' || ca >= '0' && ca <= '9') {
                    res[res_point] += ca;
                    i++;
                    if (!breakLen(i, newstr.length())) {
                        break;
                    }
                    ca = newstr.charAt(i);
                }
                i--;
            } else if (ca >= '0' && ca <= '9') {  // 处理数字
                res[res_point] = "";
                res[res_point] += ca;
                if (!breakLen(i+1, newstr.length())) {
                    break;
                }
                i++;
                ca = newstr.charAt(i);
                while (ca >= '0' && ca <= '9' || ca == '.' || ca == 'e') {
                    res[res_point] += ca;
                    i++;
                    if (!breakLen(i, newstr.length())) {
                        break;
                    }
                    ca = newstr.charAt(i);
                }
                int dian = 0,kxe=0; // 判断点和科学计数法e的数量
                for (int s = 0; s < res[res_point].length(); s++) {
                    if (res[res_point].charAt(s) == '.') {
                        dian++;
                    }
                    if (res[res_point].charAt(s) == 'e') {
                        kxe++;
                    }
                }
                if (dian > 1 || kxe > 1) {
                    res[res_point] = error();
                }
                i--;
            } else {  // 处理标识符
                if (breakLen(i + 1, newstr.length())) {
                    res[res_point] = "";
                    int ss = i + 1;
                    char cas = newstr.charAt(ss);
                    if (ca == '<') {
                        if (cas == '=') {
                            res[res_point] += "<=";
                        } else if (cas == '>') {
                            res[res_point] += "<>";
                        } else {
                            res[res_point] += "<";
                        }
                        i++;
                    } else if (ca == ':') {
                        if (cas == '=') {
                            res[res_point] = ":=";
                        } else {
                            res[res_point] += ":";
                        }
                        i++;
                    } else {
                        for (int pt=3;pt<ptable.length;pt++){
                            if (ca == ptable[pt].charAt(0)){
                                res[res_point] = ptable[pt];
                            }
                        }
                    }
                }
            }
            res_point++;
        }
        return res;
    }
}

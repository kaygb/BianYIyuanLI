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
                if (re != null) {
                    System.out.println(re);
                }
                if (re==null){
                    break;
                }
                int c=0,i=0;
                int flag = 0;
                for (int pp = 0;pp <p.length;pp++){
                    if (p[pp].equals(re)){
                        map.put("(p,"+pp+")" ,p[pp]);
                        flag=1;
                    }
                }
                if(flag==0){
                    for (int kk = 0;kk <k.length;kk++){
                        if (k[kk].equals(re)){
                            map.put("(k,"+kk+")" ,k[kk]);
                        }
                    }
                }
                if(flag==0){
                    if (re.length()!=0 && re.charAt(0) >= '0' && re.charAt(0) <= '9' ){
                        map.put("(c,"+c+")" ,re);
                        ct.add(re);
                        c++;
                    }
                }
                if(flag==0){
                    if (re.length()!=0 &&re.charAt(0) >= 'a' && re.charAt(0) <= 'z' ){
                        map.put("(i,"+i+")" ,re);
                        it.add(re);
                        i++;
                    }
                }

            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() != "error"){
                    System.out.println(entry.getKey() + "\nthe word is: " + entry.getValue());
                }else{
                    System.out.println(entry.getKey() + "\n" + entry.getValue());
                }


            }

        }



    }

    private static String[] lexical(String str , String[] ktable ,String[] ptable){
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
        String newstr = str.replace(" ", "");
//        System.out.println(newstr);
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
                            System.out.println("<=123123d");
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

//                continue;
            }
            res_point++;

        }
        return res;
    }

}

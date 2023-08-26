package com.tjetc.utils;

import com.tjetc.domain.Product;

import java.util.*;

/**
 * @belongProject: 1204-dubbo
 * @belongPackage: com.tjetc.utils
 * @author: xujirong
 * @dscription: 汉克Java笔试题，计算购买商品的加个
 * @date: 2023-08-25 15:30
 * @version: 1.0
 */
public class HankTestUtil {

    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);

        while (true){
            System.out.println("**********************************" + "\n" +
                    "*         欢迎来到结算系统         *");
            System.out.println("*           1.选择商品        *\n" +
                    "*           0.退出              *");
            System.out.println("**********************************");


            System.out.print("请输入您的选择：");

            String sel2=input.next();

            if ("1".equals(sel2)){

                System.out.print("请输入您的顾客类型(A,B,C,D)：");
                String next1 = input.next();


                List<Product> list=new ArrayList<>();

                Product product = new Product();

                product.setName("apple");
                product.setChName("苹果");
                product.setPrice("8");

                list.add(product);

                Product product01 = new Product();
                product01.setName("strawberry");
                product01.setChName("草莓");
                product01.setPrice("13");
                list.add(product01);

                Product product02 = new Product();
                product02.setName("mango");
                product02.setChName("芒果");
                product02.setPrice("20");
                list.add(product02);


                List<Product> selectList=new ArrayList<>();

                loop: while (true){
                    System.out.println("可选择的商品列表如下：");
                    System.out.println("----------------------------------------");
                    System.out.println("序号 | 名称 | 价格(元/斤)");
                    for (int i = 0; i < list.size(); i++) {

                        list.get(i).setId(i+1);
                        System.out.println((i+1)+" | "+list.get(i).getChName()+" | "+list.get(i).getPrice()+"(元/斤)");
                    }
                    System.out.println("----------------------------------------");

                    if (selectList.size()>0){
                        StringBuffer sb=new StringBuffer();
                        for (Product product1 : selectList) {
                            sb.append(product1.getChName()+"("+product1.getBuyNum()+"斤)").append(",");
                        }
                        System.out.println("已选择的商品:"+sb.toString());
                    }

                    System.out.println("请选择商品编号：");
                    int anInt = input.nextInt();
                    System.out.println(anInt);

                    boolean flag=false;
                    for (int i = 1; i <=3; i++) {
                        if (anInt==i){
                            flag=true;
                        }
                    }

                    if (!flag){
                        System.out.println("您输入的商品编号有误,请重新输入！！！");
                        continue;
                    }
                    Product product1=list.get(anInt-1);

                    System.out.println("请输入购买商品斤数：");

                    anInt = input.nextInt();
                    product1.setBuyNum(anInt);
                    selectList.add(product1);
                    loop2: while (true){
                        System.out.println("请问是否继续选择？？(是/否)");
                        String next = input.next();
                        if ("否".equals(next)){
                            break loop;
                        }else if ("是".equals(next)){
                            break loop2;
                        }else {
                            System.out.println("您输入的有误,请重新输入！！！");
                        }
                    }

                }

                System.out.println();
                System.out.println();
                System.out.println("要结算的商品列表如下：");
                System.out.println("----------------------------------------");
                System.out.println("序号 | 名称 | 价格(元/斤) | 斤");
                for (int i = 0; i < selectList.size(); i++) {

                    System.out.println((i+1)+" | "+selectList.get(i).getChName()+" | "+selectList.get(i).getPrice()+"(元/斤)"+" | "+selectList.get(i).getBuyNum());
                }
                System.out.println("----------------------------------------");

                while (true){
                    System.out.println("请问是否立即结算？？(是/否)");
                    sel2=input.next();
                    if ("否".equals(sel2)){
                        break;
                    }else if ("是".equals(sel2)){

                        Map<String, Object> map = purchaseMethod(selectList,next1);

                        System.out.println();
                        System.out.println();
                        System.out.println("=================================================");
                        System.out.println("顾客名称：顾客 "+map.get("userName"));
                        System.out.println("结算时间："+new Date()+"\n序列号:"+ UUID.randomUUID());
                        System.out.println("购买商品清单如下：");
                        for (Product product1 : selectList) {
                            System.out.println(product1.getChName()+":"+product1.getPrice()+"(元/斤),"+product1.getBuyNum()+"斤,单个商品总价格为"+(product1.getBuyNum())*(Integer.parseInt(product1.getPrice()))+"元");
                        }
                        System.out.println("---------------------");
                        System.out.println("是否有优惠："+map.get("discount"));
                        System.out.println("---------------------");
                        System.out.println("购买商品总计为："+map.get("sumPrice")+"元");
                        System.out.println("======================谢谢惠顾====================");
                        break;
                    }else {
                        System.out.println("您输入的有误,请重新输入！！！");
                    }
                }



            }else {
                return;
            }

        }
    }

    /**
    * @descriprion: 计算购买商品总价格的方法，可使用价格表，根据商品名称获取价格
    * @author: xujirong
    * @date: 2023/8/25 15:32
    * @param userName 顾客购买的商品，计算场景标识
    * @return: void
    */
    public static Map<String,Object> purchaseMethod(List<Product> selectList, String userName){


        //商品购买总价格
        double sumPrice=0;

        Map<String,Object> map=new HashMap<>(16);

        //判断草莓是否已打折
        boolean flagStra=false;

        Calendar cld=Calendar.getInstance();

        int	h=cld.get(Calendar.HOUR_OF_DAY);

        for (Product product : selectList) {

            int price = Integer.parseInt(product.getPrice());

            if ("strawberry".equals(product.getName())){
                //问题3.顾客 C 在超市购买了若干斤苹果、 草莓和芒果,草莓限时打 8 折，每天下午2点到4点
                if (h>=14 && h<=16){
                    sumPrice+=product.getBuyNum()*(price*0.8);
                    map.put("discount","是，草莓限时打 8 折，每天下午2点到4点");
                }else {
                    sumPrice+=product.getBuyNum()*(price);

                    if ("".equals(map.get("discount"))){
                        map.put("discount","否");
                    }
                }
                flagStra=true;
            }else {
                sumPrice+=product.getBuyNum()*(price);

                if ("".equals(map.get("discount")) || map.get("discount")==null){
                    map.put("discount","否");
                }
            }
        }

        //问题4.顾客 D 在超市购买了若干斤苹果、 草莓和芒果,购物满 100 减 10 块
        if (sumPrice>=100){
            sumPrice=sumPrice-10;

            if (flagStra){
                map.put("discount","是，购物满 100 减 10 块,草莓限时打 8 折，每天下午2点到4点");
            }else {
                map.put("discount","是，购物满 100 减 10 块");
            }

        }

        map.put("sumPrice",sumPrice);
        map.put("userName",userName);


        return map;

    }

}

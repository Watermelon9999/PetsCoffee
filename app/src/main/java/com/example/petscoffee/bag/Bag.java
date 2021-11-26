package com.example.petscoffee.bag;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.petscoffee.goods.Goods;
import com.example.petscoffee.goods.GoodsConverter;
import com.example.petscoffee.goods.Keys;

import java.io.Serializable;
import java.util.ArrayList;
@Entity
@TypeConverters({GoodsConverter.class})
public class Bag implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private static ArrayList<Goods> bag = new ArrayList<Goods>();// 仓库物品动态数组

    public Bag() {
        bag.add(new Keys());
    }

    public int search(String name) {// 仓库物品搜索功能,返回物品在数组中的索引
        int index = -1;
        for (int i = 0; i < bag.size(); i++) {
            if (bag.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }
        return index;
    }


    public float addGood(String name, int number) throws Exception {// 新增物品功能,返回物品的价格，方便商店扣费
        float price;
        int index = search(name);//search查找物品是否存在，如果存在返回其序号
        if (index != -1) {// 如果仓库里已经有该物品，增加该物品数量
            bag.get(index).setNumber(number);// 增加该物品数量
            price = bag.get(index).getPrice();// 设置物品价格
        } else {//如果仓库中没有该物品
            bag.add(factory(name));// 调用工厂类
            bag.get(bag.size() - 1).setNumber(number);//设置物品数量
            price = bag.get(bag.size() - 1).getPrice();// 设置物品价格
        }
        return price;
    }

    public float reduce(String name, int number) throws Exception {// 减少物品功能,可用于商场贩卖道具，或是日常消耗道具；返回物品的价格，方便商店增加金钱
        float price;
        int index = search(name);//search查找物品是否存在，如果存在返回其序号
        if (bag.get(index).getNumber() >= number) {//如果该物品数量大于减少数量
            bag.get(index).setNumber(-number);
            price = bag.get(index).getPrice() / 2;//贩卖价格减半；
        } else {
            throw new Exception(name + "不足，不能进行此操作");//抛出异常
        }
        return price;
    }

    public Goods factory(String name) throws Exception {// 工厂类，当增加新商品时，修改此方法
        Goods goods = null;
        goods = (Goods) Class.forName(Goods.class.getName().replace("Goods",name)).getConstructor().newInstance();
        return goods;
    }

    public int getSize() {
        return bag.size();
    }

    public ArrayList<Goods> getBag() {
        return bag;
    }

}

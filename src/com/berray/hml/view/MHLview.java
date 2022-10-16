package com.berray.hml.view;

import com.berray.hml.dao.DiningTableDAO;
import com.berray.hml.domain.Bill;
import com.berray.hml.domain.DiningTable;
import com.berray.hml.domain.Employee;
import com.berray.hml.domain.Menu;
import com.berray.hml.service.BillService;
import com.berray.hml.service.DiningTableService;
import com.berray.hml.service.EmployeeService;
import com.berray.hml.service.MenuService;
import com.berray.hml.utils.Utility;

import java.util.List;

public class MHLview {
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();
    private boolean loop = true;
    public static void main(String[] args) {
        new MHLview().menu();
    }
    public void menu(){

        while (loop) {
            System.out.println("==============餐廳系統==============");
            System.out.println("\t\t1 登入餐廳系統");
            System.out.println("\t\t2 退出餐廳系統");
            System.out.print("請輸入：");
            String str1 = Utility.readString();
            switch (str1) {
                case "1":
                    System.out.print("輸入員工號碼：");
                    String empID = Utility.readString();
                    System.out.print("輸入密   碼：");
                    String pwd = Utility.readString();
                    Employee employee = employeeService.getEmployeeIdAndPwd(empID, pwd);
                    if(employee != null) {
                        System.out.println(" =============" + employee.getName() + "登入成功============");
                        while(loop) {
                            System.out.println("==============餐廳系統介面==============");
                            System.out.println("\t\t1 顯示餐桌狀態");
                            System.out.println("\t\t2 預定餐桌");
                            System.out.println("\t\t3 餐廳菜單");
                            System.out.println("\t\t4 點餐服務");
                            System.out.println("\t\t5 查看賬單");
                            System.out.println("\t\t6 結帳");
                            System.out.println("\t\t9 退出餐廳系統");
                            System.out.print("請輸入：");
                            String str2 = Utility.readString();
                            switch (str2) {
                                case "1"://餐桌狀態
                                    listDiningTable();
                                    break;
                                case "2"://訂位
                                    orderDiningTable();
                                    break;
                                case "3"://菜單
                                    listMenu();
                                    break;
                                case "4"://點餐
                                    orderMenu();
                                    break;
                                case "5"://帳單
                                    getBill();
                                    break;
                                case "6"://結帳
                                    checkOut();
                                    break;
                                case "9"://退出
                                    loop = false;
                                    System.out.println("--退出點餐系統--");
                                    break;
                            }
                        }
                    }else{
                        System.out.println("===============登入失敗===============\t");
                    }
                    break;
                case "2":
                    System.out.println("--退出餐廳系統--");
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
    //查看帳單
    public void getBill(){
        System.out.print("輸入結帳桌號：");
        int diningTableId = Utility.readInt();
        List<Bill> billList = billService.getBillList(diningTableId);
        System.out.println("桌號\t\t餐點\t\t數量\t\t金額");
        for (Bill bill : billList){
            System.out.println(bill);
        }
    }
    //點菜
    public void orderMenu(){
        System.out.print("輸入桌   號：");
        int diningTableId = Utility.readInt();
        System.out.print("輸入餐點編號：");
        int menuId = Utility.readInt();
        System.out.print("輸入餐點數量：");
        int nums = Utility.readInt();
        Menu menu = menuService.getMenu(menuId);
        DiningTable diningTable = diningTableService.getDiningTable(diningTableId);
        if (diningTable == null) {
            System.out.println("           (桌號不存在)         ");
            return;
        }
        if(diningTable.getState().equals("空")){
            System.out.println("            (尚未訂位)         ");
            return;
        }
        if(menu == null){
            System.out.println("           (餐點不存在)         ");
            return;
        }
        if(diningTableService.setState(diningTableId)) {
            int i = billService.orderBill(menu.getId(), nums, menu.getPrice() * nums, diningTableId);
            System.out.println("點餐成功");
        }

    }
    public void checkOut(){
        System.out.print("輸入結帳桌號：");
        int diningTableId = Utility.readInt();
        System.out.print("確認結帳(y/n)：");
        String ans = Utility.readString();
        if(ans.equals("n")){
            return;
        }else{
            Bill billSingle = billService.getBillSingle(diningTableId);
            double billtotal = billService.getBilltotal(diningTableId);
            System.out.println("一共是 '" + billtotal + "' 元");
            System.out.print("輸入結帳方式：");
            String checkoutMethods = Utility.readString();
            System.out.println("\n");
            if(billService.setBillState(checkoutMethods,diningTableId)){
                diningTableService.setStateNull(diningTableId);

                System.out.println("         (謝謝惠顧 歡迎再次光臨)");
                return;
            }
        }
    }
    //菜單
    public void listMenu(){
        System.out.println("   ---------顯示餐桌狀態---------");
        List<Menu> menuList = menuService.getMenuList();
        System.out.println("編號\t\t\t餐點\t\t\t類型\t\t\t價格");
        for(Menu menu : menuList){
            System.out.println(menu);
        }
        System.out.println("   -----------------------------");
    }
    //訂桌
    public void orderDiningTable(){
        System.out.print("輸入桌號(9 退出)：");
        int orderID = Utility.readInt();
        DiningTable diningTable = diningTableService.getDiningTable(orderID);
        if(orderID != 9) {
            if (diningTable == null) {
                System.out.println("           (桌號不存在)         ");
                return;
            }
            if (!(diningTable.getState().equals("空"))) {
                System.out.println("         (" + orderID + "桌已訂位)         ");
                return;
            } else {

            }
            System.out.print("輸入訂桌姓名：");
            String orderName = Utility.readString();
            System.out.print("輸入訂桌電話：");
            String orderTel = Utility.readString();
            diningTableService.orderDiningTable(orderID, orderName, orderTel);
            System.out.println("          " + orderName + " 訂位成功         ");
        }else{
            return;
        }

    }
    //顯示餐桌狀態
    public void listDiningTable(){
        System.out.println("   ---------顯示餐桌狀態---------");
        System.out.println("桌號\t\t餐桌狀態");
        List<DiningTable> list = diningTableService.list();
        for(DiningTable diningTable : list){
            System.out.println(diningTable);
        }
        System.out.println("   -----------------------------");
    }
}

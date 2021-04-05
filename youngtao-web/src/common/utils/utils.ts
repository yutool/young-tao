import Cookies from 'js-cookie';

class Utils {
  private setOrderItem(data: any) {
    const randKey = Math.random().toString(36).slice(-6)
    const expires = new Date(new Date().getTime() + 60 * 60 * 1000);
    Cookies.set(randKey, JSON.stringify(data), { expires });
    return randKey
  }
  
  private getOrderItem(randKey: any) {
    const data: any = Cookies.get(randKey);
    return JSON.parse(data);
  }

  // 拷贝对象
  private copyOf(obj: any) {
    return JSON.parse(JSON.stringify(obj))
  }
}

export default new Utils();

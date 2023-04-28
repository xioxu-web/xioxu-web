package io.microservices.shop.goods.service;

import io.microservices.shop.goods.api.domain.Goods;

public interface SpuService {

    /***
     * 新增
     * @param goods
     */
    void add(Goods goods);
}

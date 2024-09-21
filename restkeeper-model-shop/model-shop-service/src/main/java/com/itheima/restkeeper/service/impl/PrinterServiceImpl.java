package com.itheima.restkeeper.service.impl;

import com.itheima.restkeeper.pojo.Printer;
import com.itheima.restkeeper.mapper.PrinterMapper;
import com.itheima.restkeeper.service.IPrinterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description：打印机表 服务实现类
 */
@Service
public class PrinterServiceImpl extends ServiceImpl<PrinterMapper, Printer> implements IPrinterService {

}

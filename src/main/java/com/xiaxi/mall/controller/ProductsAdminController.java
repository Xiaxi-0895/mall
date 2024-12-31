package com.xiaxi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.model.request.AddGoodsReq;
import com.xiaxi.mall.model.request.UpdateProductReq;
import com.xiaxi.mall.service.impl.ProductsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class ProductsAdminController {
    @Value("${file.upload.dir}")
    String location;
    @Autowired
    private ProductsServiceImpl goodsService;

    @Operation(summary = "新增商品")
    @PostMapping("/goods/add")
    public ApiRestResponse addGoods(@Valid AddGoodsReq addGoodsReq) {
        goodsService.addGoods(addGoodsReq);
        return ApiRestResponse.success();
    }

    @Operation(summary = "商品图片上传")
    @PostMapping("/upload/file")
    public ApiRestResponse uploadPicture(HttpServletRequest request, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  //获取后缀
        UUID uuid = UUID.randomUUID();  //生成一个唯一的标识符（UUID），并将其转换为字符串。UUID保证文件名唯一，避免了文件名冲突的风险。
        String newFileName = uuid.toString() + suffixName;
        File fileDirectory = new File(location);
        File destFile = new File(location +"/"+ newFileName);
        if (!fileDirectory.exists()) {
            if(!fileDirectory.mkdirs()){    //如果文件夹不存在则新建文件夹
                throw new MallException(MallExceptionEnum.MAKE_DIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);  //将上传的文件保存到目标路径destFile。transferTo方法会将上传的MultipartFile传输到目标文件中。
             return ApiRestResponse.success(getHost(new URI(request.getRequestURL()+""))+"/images/"+newFileName);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getHost(URI uri){
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @PostMapping("/goods/update")
    @Operation(summary = "更新商品信息")
    public ApiRestResponse updateGoods(UpdateProductReq updateProductReq) {
        goodsService.updateGoods(updateProductReq);
        return ApiRestResponse.success();
    }

    @PostMapping("/goods/delete")
    @Operation(summary = "删除商品信息")
    public ApiRestResponse deleteGoods(int id) {
        goodsService.deleteGoods(id);
        return ApiRestResponse.success();
    }

    @PostMapping("/goods/batchUpdateSellStatus")
    @Operation(summary = "集体更新商品信息")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam List<Integer> ids, int sellStatus) {
        goodsService.batchUpdateGoods(ids,sellStatus);
        return ApiRestResponse.success();
    }

    @GetMapping("/goods/list")
    @Operation(summary = "管理员获取商品列表")
    public ApiRestResponse listGoods(int pageNo, int pageSize) {
        PageInfo info = goodsService.listForAdmin(pageNo, pageSize);
        return ApiRestResponse.success(info);
    }


}

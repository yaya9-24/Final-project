package com.project.zerowasteshop.event;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class EventController {

    @Autowired
    private EventService service;

    // application.properties에서 설정한 변수(file.dir)를 DI
    @Value("${file.dir}")
    private String realPath;

    @GetMapping("/event/insert")
    public String e_insert() {
        log.info("/event/insert");
        return "event/insert";
    }

    @GetMapping("/event/update")
    public String e_update(EventVO vo, Model model) {
        log.info("/event/update");
        log.info("vo:{}", vo);

        EventVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "event/update";
    }

    @GetMapping("/event/delete")
    public String e_delete() {
        log.info("/event/delete");
        return "event/delete";
    }

    @GetMapping("/event/selectOne")
    public String e_selectOne(EventVO vo, Model model) {
        log.info("/event/selectOne");
        log.info("vo:{}", vo);

        EventVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "event/selectOne";
    }

    @GetMapping("/event/selectAll")
    public String e_selectAll(Model model) {
        log.info("/event/selectAll");

        List<EventVO> list = service.selectAll();
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        return "event/selectAll";
    }

    @GetMapping("/event/selectAllPageBlock")
    public String e_selectAllPageBlock(Model model,
                                       @RequestParam(defaultValue = "1") int cpage,
                                       @RequestParam(defaultValue = "5") int pageBlock) {
        log.info("/event/selectAllPageBlock");
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<EventVO> list = service.selectAllPageBlock(cpage, pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getTotalRows();
        log.info("total_rows:{}", total_rows);

        int totalPageCount = (total_rows + pageBlock - 1) / pageBlock;
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "event/selectAll";
    }

    @GetMapping("/event/searchList")
    public String e_searchList(Model model,
                               @RequestParam(defaultValue = "") String searchWord) {
        log.info("/event/searchList");
        log.info("searchWord:{}", searchWord);

        List<EventVO> list = service.searchList(searchWord);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        return "event/selectAll";
    }

    @GetMapping("/event/searchListPageBlock")
    public String e_searchListPageBlock(Model model,
                                        @RequestParam(defaultValue = "") String searchWord,
                                        @RequestParam(defaultValue = "1") int cpage,
                                        @RequestParam(defaultValue = "5") int pageBlock) {
        log.info("/event/searchListPageBlock");
        log.info("searchWord:{}", searchWord);
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<EventVO> list = service.searchListPageBlock(searchWord, cpage, pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getSearchTotalRows(searchWord);
        log.info("total_rows:{}", total_rows);

        int totalPageCount = (total_rows + pageBlock - 1) / pageBlock;
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "event/selectAll";
    }

    @PostMapping("/event/insertOK")
    public String e_insertOK(EventVO vo) throws IllegalStateException, IOException {
        log.info("/event/insertOK");
        log.info("vo:{}", vo);

        log.info(realPath);

        MultipartFile file = vo.getFile();
        String originName = file.getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName == null || originName.isEmpty()) {
            vo.setEvent_img("default.png");
        } else {
            String saveName = "img_" + System.currentTimeMillis()
                    + originName.substring(originName.lastIndexOf("."));
            log.info("saveName:{}", saveName);
            vo.setEvent_img(saveName);

            File f = new File(realPath, saveName);
            file.transferTo(f);

            // 썸네일 생성
            BufferedImage originalImage = ImageIO.read(f);
            BufferedImage thumbnailImage = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumbnailImage.createGraphics();
            graphic.drawImage(originalImage, 0, 0, 50, 50, null);

            File thumbnailFile = new File(realPath, "thumb_" + saveName);
            ImageIO.write(thumbnailImage, saveName.substring(saveName.lastIndexOf(".") + 1), thumbnailFile);
        }

        int result = service.insertOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/event/selectAll";
        } else {
            return "redirect:/event/insert";
        }
    }

    @PostMapping("/event/deleteOK")
    public String e_deleteOK(EventVO vo) {
        log.info("/event/deleteOK");
        log.info("vo:{}", vo);

        int result = service.deleteOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/event/selectAll";
        } else {
            return "redirect:/event/delete?event_num=" + vo.getEvent_num();
        }
    }

    @PostMapping("/event/updateOK")
    public String e_updateOK(EventVO vo) throws IllegalStateException, IOException {
        log.info("/event/updateOK");
        log.info("vo:{}", vo);

        log.info(realPath);

        MultipartFile file = vo.getFile();
        String originName = file.getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName == null || originName.isEmpty()) {
            vo.setEvent_img(vo.getEvent_img());
        } else {
            String saveName = "img_" + System.currentTimeMillis()
                    + originName.substring(originName.lastIndexOf("."));
            log.info("saveName:{}", saveName);
            vo.setEvent_img(saveName);

            File f = new File(realPath, saveName);
            file.transferTo(f);

            // 썸네일 생성
            BufferedImage originalImage = ImageIO.read(f);
            BufferedImage thumbnailImage = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumbnailImage.createGraphics();
            graphic.drawImage(originalImage, 0, 0, 50, 50, null);

            File thumbnailFile = new File(realPath, "thumb_" + saveName);
            ImageIO.write(thumbnailImage, saveName.substring(saveName.lastIndexOf(".") + 1), thumbnailFile);
        }

        int result = service.updateOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/event/selectOne?event_num=" + vo.getEvent_num();
        } else {
            return "redirect:/event/update?event_num=" + vo.getEvent_num();
        }
    }
}
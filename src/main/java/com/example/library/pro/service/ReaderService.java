package com.example.library.pro.service;

import com.example.library.pro.dao.ReaderDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Reader;
import com.example.library.pro.vo.ReaderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class ReaderService {

    @Autowired
    ReaderDao readerDao;

    public Reader registerReader(ReaderVo readerVo) {
        Reader reader = new Reader();
        reader.setAddress(readerVo.getAddress());
        reader.setName(readerVo.getName());
        reader.setType(readerVo.getType());

        if (ObjectUtils.isEmpty(readerVo.getPhoneNumber()) || ObjectUtils.isEmpty(readerVo.getPassword())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "手机号和密码不能为空");
        }

        Optional<Reader> res = readerDao.findByPhoneNumber(readerVo.getPhoneNumber());
        if (res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该手机号已经被注册");
        }


        reader.setPhoneNumber(readerVo.getPhoneNumber());
        reader.setPassword(readerVo.getPassword());
        return readerDao.save(reader);
    }


    public void cancelRegister(Long id) {
        Optional<Reader> res = readerDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }
        readerDao.deleteById(id);

    }

    public Reader login(String phoneNumber, String password) {
        if (ObjectUtils.isEmpty(phoneNumber) || ObjectUtils.isEmpty(password)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "手机号和密码不能为空");
        }
        Optional<Reader> res = readerDao.findByPhoneNumber(phoneNumber);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }
        if (!password.equals(res.get().getPassword())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "密码不正确");
        }

        return res.get();
    }

    public Reader getReaderById(Long id) {
        Optional<Reader> res = readerDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }

        return res.get();
    }
}

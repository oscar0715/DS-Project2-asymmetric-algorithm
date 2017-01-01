# DS-Project2-asymmetric-algorithm
非对称加密算法实现 client 端 和 server 端的 socket 通信

[Task1 的README链接](https://github.com/oscar0715/DS-Project2-symmetric-algorithm/blob/master/README.md)

Task1 是用对称加密，但是要求 client 和 server 端在沟通之前都拥有对称密钥。Task2 中，server 端先用非对称加密算法 RSA 将对称密钥发送给 client 端，然后 client 和 server 继续用对称加密算法 TEA 进行加密沟通。这解决了分配对称加密算法公钥的麻烦。

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.my.sasa.bistrovic.springbootelasticsearch;

import java.util.List;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {
/*
    List<Book> findAll();

    <S extends Book> S save(S entity);

    void deleteById(String id);

    Optional<Book> findById(String id);
*/
}

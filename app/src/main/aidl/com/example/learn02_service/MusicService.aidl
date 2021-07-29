// MusicService.aidl
package com.example.learn02_service;

// Declare any non-default types here with import statements

interface MusicService {
       void play();
       void pause();
       double getProgress();
}
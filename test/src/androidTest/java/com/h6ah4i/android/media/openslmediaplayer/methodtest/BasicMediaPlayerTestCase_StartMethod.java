/*
 *    Copyright (C) 2014 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package com.h6ah4i.android.media.openslmediaplayer.methodtest;

import junit.framework.TestSuite;

import com.h6ah4i.android.media.IBasicMediaPlayer;
import com.h6ah4i.android.media.IMediaPlayerFactory;
import com.h6ah4i.android.media.openslmediaplayer.base.BasicMediaPlayerStateTestCaseBase;
import com.h6ah4i.android.media.openslmediaplayer.base.BasicMediaPlayerTestCaseBase;
import com.h6ah4i.android.media.openslmediaplayer.utils.CompletionListenerObject;
import com.h6ah4i.android.media.openslmediaplayer.utils.ErrorListenerObject;
import com.h6ah4i.android.media.openslmediaplayer.testing.ParameterizedTestArgs;

public class BasicMediaPlayerTestCase_StartMethod
        extends BasicMediaPlayerStateTestCaseBase {

    public static TestSuite buildTestSuite(Class<? extends IMediaPlayerFactory> factoryClazz) {
        return BasicMediaPlayerTestCaseBase.buildBasicTestSuite(
                BasicMediaPlayerTestCase_StartMethod.class, factoryClazz);
    }

    public BasicMediaPlayerTestCase_StartMethod(ParameterizedTestArgs args) {
        super(args);
    }

//    private void expectsNoErrors(IBasicMediaPlayer player) {
//        Object sharedSyncObj = new Object();
//        ErrorListenerObject err = new ErrorListenerObject(sharedSyncObj, false);
//        CompletionListenerObject comp = new CompletionListenerObject(sharedSyncObj);
//
//        player.setOnErrorListener(err);
//        player.setOnCompletionListener(comp);
//
//        player.start();
//
//        if (comp.await(SHORT_EVENT_WAIT_DURATION)) {
//            fail(comp + ", " + err);
//        }
//
//        assertFalse(comp.occurred());
//        assertFalse(err.occurred());
//    }

    private void expectsPlaybackCompletionWithNoErrors(IBasicMediaPlayer player) {
        Object sharedSyncObj = new Object();
        ErrorListenerObject err = new ErrorListenerObject(sharedSyncObj, false);
        CompletionListenerObject comp = new CompletionListenerObject(sharedSyncObj);

        player.setOnErrorListener(err);
        player.setOnCompletionListener(comp);

        player.start();

        if (!comp.await(determineWaitCompletionTime(player))) {
            fail();
        }

        assertTrue(comp.occurred());
        assertFalse(err.occurred());
    }

    private void expectsErrorCallback(IBasicMediaPlayer player) {
        Object sharedSyncObj = new Object();
        ErrorListenerObject err = new ErrorListenerObject(sharedSyncObj, false);
        CompletionListenerObject comp = new CompletionListenerObject(sharedSyncObj);

        player.setOnErrorListener(err);
        player.setOnCompletionListener(comp);

        player.start();

        if (!comp.await(determineWaitCompletionTime(player))) {
            fail();
        }

        assertTrue(comp.occurred());
        assertTrue(err.occurred());
    }

    private void expectsIllegalStateException(IBasicMediaPlayer player) {
        try {
            player.start();
        } catch (IllegalStateException e) {
            return;
        }
        fail();
    }

    @Override
    protected void onTestStateIdle(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStateInitialized(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStatePreparing(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStatePrepared(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsPlaybackCompletionWithNoErrors(player);
    }

    @Override
    protected void onTestStateStarted(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsPlaybackCompletionWithNoErrors(player);
    }

    @Override
    protected void onTestStatePaused(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsPlaybackCompletionWithNoErrors(player);
    }

    @Override
    protected void onTestStateStopped(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStatePlaybackCompleted(IBasicMediaPlayer player, Object args)
            throws Throwable {
        expectsPlaybackCompletionWithNoErrors(player);
    }

    @Override
    protected void onTestStateErrorBeforePrepared(IBasicMediaPlayer player, Object args)
            throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStateErrorAfterPrepared(IBasicMediaPlayer player, Object args)
            throws Throwable {
        expectsErrorCallback(player);
    }

    @Override
    protected void onTestStateEnd(IBasicMediaPlayer player, Object args) throws Throwable {
        expectsIllegalStateException(player);
    }
}
